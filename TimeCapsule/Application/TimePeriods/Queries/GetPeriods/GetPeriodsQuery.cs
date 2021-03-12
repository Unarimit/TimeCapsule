using AutoMapper;
using AutoMapper.QueryableExtensions;
using MediatR;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;

namespace TimeCapsule.Application.TimePeriods.Queries.GetPeriods
{
    public class GetPeriodsQuery : IRequest<PeriodsVm>
    {
        public string Username { get; set; }

        public int Calendar { get; set; }
    }

    public class GetPeriodsQueryHandler : IRequestHandler<GetPeriodsQuery, PeriodsVm>
    {
        private readonly IApplicationDbContext _context;
        private readonly IMapper _mapper;
        public GetPeriodsQueryHandler(IApplicationDbContext context, IMapper mapper)
        {
            _context = context;
            _mapper = mapper;
        }
        public async Task<PeriodsVm> Handle(GetPeriodsQuery request, CancellationToken cancellationToken)
        {
            var periods = await _context.Periods.AsNoTracking()
                .Include(x => x.Daily)
                    .ThenInclude(x => x.User)
                .Include(x => x.Task)
                    .ThenInclude(x => x.TaskClass)
                .Where(x => x.Daily.User.Username == request.Username && x.Daily.Calendar == request.Calendar)
                .OrderByDescending(x => x.BeginTime)
                .ProjectTo<PeriodDto>(_mapper.ConfigurationProvider)
                .ToListAsync(cancellationToken);

            return new PeriodsVm { Periods = periods};
        }
    }
}
