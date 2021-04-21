using MediatR;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using TimeCapsule.Application.AndroidSync.AndroidDtos;
using TimeCapsule.Application.Common.Exceptions;
using TimeCapsule.Application.Common.Interfaces;

namespace TimeCapsule.Application.AndroidSync.Commands.SyncPeriods
{
    public class SyncPeriodsCommand : IRequest<SyncPeriodsVm>
    {
        public IList<ATimePeriod> ATimePeriods { get; set; }

    }
    public class SyncPeriodsCommandHandler : IRequestHandler<SyncPeriodsCommand, SyncPeriodsVm>
    {
        IApplicationDbContext _context;
        public SyncPeriodsCommandHandler(IApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<SyncPeriodsVm> Handle(SyncPeriodsCommand request, CancellationToken cancellationToken)
        {
            foreach(var periodDto in request.ATimePeriods)
            {
                var task = await _context.Tasks.FirstOrDefaultAsync(x => x.Id == periodDto.TaskId);
                if(task == null)
                {
                    throw new TaskException.TaskUnexistException(periodDto.TaskId);
                }
                var period = await _context.Periods.FirstOrDefaultAsync(x => x.Id == periodDto.Id);
                if (period == null)
                {
                    _context.Periods.Add(periodDto.ToTimePeriod(task));
                }
                else
                {
                    // TODO: check last modified
                    period = periodDto.ToTimePeriod(task);
                }
            }
            return new SyncPeriodsVm { Change = await _context.SaveChangesAsync(new CancellationToken()) };
        }
    }
}
