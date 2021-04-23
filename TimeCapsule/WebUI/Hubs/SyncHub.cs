using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.SignalR;
using Microsoft.EntityFrameworkCore;
using TimeCapsule.Application.Common.Interfaces;

namespace TimeCapsule.WebUI.Hubs
{
    public class SyncHub : Hub
    {
        IApplicationDbContext _context;
        public SyncHub(IApplicationDbContext context)
        {
            _context = context;
        }

        public async Task StartPeriod(Guid taskId)
        {
            var task = await _context.Tasks.FirstOrDefaultAsync(x => x.Id == taskId);
            _context.Periods.Add(new Domain.Entities.TimePeriod { Task = task, BeginTime = DateTime.Now, IsFinish = false });

            await _context.SaveChangesAsync(new System.Threading.CancellationToken());
        }

        public async Task FinishPeriod(Guid periodId)
        {
            var period = await _context.Periods.FirstOrDefaultAsync(x => x.Id == periodId);
            period.IsFinish = true;
            period.LastModified = DateTime.Now;
            period.EndTime = DateTime.Now;

            await _context.SaveChangesAsync(new System.Threading.CancellationToken());
        }
    }
}
