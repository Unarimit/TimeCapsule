using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using AutoMapper;
using AutoMapper.QueryableExtensions;
using System;
using System.Linq;
using TimeCapsule.Application.Common.Model;

namespace TimeCapsule.Application.TimeDailies.Queries.GetDaily
{
    public class GetDailyQuery : IRequest<DailyVm>
    {
        public Guid Id { get; set; }

        public string Username { get; set; }
    }

    public class GetDailyQueryHandler : IRequestHandler<GetDailyQuery, DailyVm>
    {
        private readonly IApplicationDbContext _context;
        private readonly IMapper _mapper;

        public GetDailyQueryHandler(IApplicationDbContext context, IMapper mapper)
        {
            _context = context;
            _mapper = mapper;
        }

        public async Task<DailyVm> Handle(GetDailyQuery request, CancellationToken cancellationToken)
        {
            var daily_check = await _context.Dailies.AsNoTracking()
                .Include(x => x.User)
                .FirstOrDefaultAsync(x => x.Id == request.Id);

            if (daily_check == null)
                return null;

            if (daily_check.User.Username != request.Username)
                return null;

            var daily = await _context.Dailies.AsNoTracking()
                .Include(x => x.Periods)
                    .ThenInclude(x => x.Task)
                        .ThenInclude(x => x.TaskClass)
                .Where(x => x.Id == request.Id)
                .ProjectTo<DailyDto>(_mapper.ConfigurationProvider)
                .FirstOrDefaultAsync(cancellationToken);

            return new DailyVm { Daily = daily };
        }
    }
}

