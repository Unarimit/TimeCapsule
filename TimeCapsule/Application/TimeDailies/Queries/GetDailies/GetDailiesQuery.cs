using AutoMapper;
using AutoMapper.QueryableExtensions;
using MediatR;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using TimeCapsule.Application.Common.Interfaces;

namespace TimeCapsule.Application.TimeDailies.Queries.GetDailies
{
    public class GetDailiesQuery : IRequest<DailiesVm>
    {
        public string Username { get; set; }

        public int Take { get; set; }

        public int Skip { get; set; }
    }

    public class GetDailiesQueryHandler : IRequestHandler<GetDailiesQuery, DailiesVm>
    {
        IApplicationDbContext _context;
        IMapper _mapper;

        public GetDailiesQueryHandler(IApplicationDbContext context, IMapper mapper)
        {
            _context = context;
            _mapper = mapper;
        }


        public async Task<DailiesVm> Handle(GetDailiesQuery request, CancellationToken cancellationToken)
        {
            var dailies = await _context.Dailies.AsNoTracking()
                .Include(x => x.User)
                .Where(x => x.User.Username == request.Username)
                .OrderByDescending(x => x.Calendar)
                .Skip(request.Skip)
                .Take(request.Take)
                .ProjectTo<DailyDto>(_mapper.ConfigurationProvider)
                .ToListAsync(cancellationToken);

            int total = await _context.Dailies.AsNoTracking()
                .Include(x => x.User)
                .Where(x => x.User.Username == request.Username)
                .CountAsync(cancellationToken);

            return new DailiesVm { Dailies = dailies, Total = total };
        }
    }
}
