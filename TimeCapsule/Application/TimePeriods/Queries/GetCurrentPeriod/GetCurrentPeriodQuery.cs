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

namespace TimeCapsule.Application.TimePeriods.Queries.GetCurrentPeriod
{
    public class GetCurrentPeriodQuery : IRequest<PeriodVm>
    {
        public string Username { get; set; }

    }

    public class GetCurrentPeriodQueryHandler : IRequestHandler<GetCurrentPeriodQuery, PeriodVm>
    {
        private readonly IApplicationDbContext _context;
        private readonly IMapper _mapper;
        public GetCurrentPeriodQueryHandler(IApplicationDbContext context, IMapper mapper)
        {
            _context = context;
            _mapper = mapper;
        }
        public async Task<PeriodVm> Handle(GetCurrentPeriodQuery request, CancellationToken cancellationToken)
        {
            var period = await _context.Periods.AsNoTracking()
                .Include(x => x.Task)
                    .ThenInclude(x => x.TaskClass)
                .Include(x => x.Task)
                    .ThenInclude(x => x.User)
                .Where(x => x.Task.User.Username == request.Username && x.IsFinish == false)
                .ProjectTo<PeriodDto>(_mapper.ConfigurationProvider)
                .FirstOrDefaultAsync();
            
            return period;
        }
    }
}
