using MediatR;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using TimeCapsule.Application.AndroidSync.AndroidDtos;
using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Application.Utils;

namespace TimeCapsule.Application.AndroidSync.Queries.GetPeriods
{
    public class GetPeriodsQuery : IRequest<PeriodsVm>
    {
        public long LastSync { get; set; }
    }

    public class GetPeriodsQueryHandler : IRequestHandler<GetPeriodsQuery, PeriodsVm>
    {
        IApplicationDbContext _context;
        public GetPeriodsQueryHandler(IApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<PeriodsVm> Handle(GetPeriodsQuery request, CancellationToken cancellationToken)
        {
            var count = await _context.Periods.AsNoTracking()
                .Where(x => x.LastModified > TimeHelper.ConvertFromUnixTimestamp(request.LastSync + 1))
                .CountAsync();

            var list = await _context.Periods.AsNoTracking()
                .Include(x => x.Task)
                .Where(x => x.LastModified > TimeHelper.ConvertFromUnixTimestamp(request.LastSync + 1))
                .OrderBy(x => x.LastModified)
                .Take(500)
                .ToListAsync();
            var temp = TimeHelper.ConvertFromUnixTimestamp(request.LastSync);
            var result = new List<ATimePeriod>();
            foreach(var x in list)
            {
                result.Add(ATimePeriod.ConvertFromTimePeriod(x));
            }

            if(count <= 500)
            {
                return new PeriodsVm { ATimePeriods = result, IsFinish = true };
            }
            else
            {
                return new PeriodsVm { ATimePeriods = result, IsFinish = false };
            }

        }
    }
}
