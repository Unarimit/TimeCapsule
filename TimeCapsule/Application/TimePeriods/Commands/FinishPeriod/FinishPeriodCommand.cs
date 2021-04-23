using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using TimeCapsule.Application.Common.Model;
using System;
using TimeCapsule.Domain.Enums;

namespace TimeCapsule.Application.TimePeriods.Commands.FinishPeriod
{
    public class FinishPeriodCommand : IRequest<CommonResult>
    {
        public string Username { get; set; }

        public Guid Id { get; set; }

        public bool IsFinish { get; set; }

        public DateTime? EndTime { get; set; }

        // TODO: implement it later
        // public TimePeriodAssess Assess { get; set; }

    }

    public class FinishPeriodCommandHandler : IRequestHandler<FinishPeriodCommand, CommonResult>
    {
        private readonly IApplicationDbContext _context;

        public FinishPeriodCommandHandler(IApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<CommonResult> Handle(FinishPeriodCommand request, CancellationToken cancellationToken)
        {
            var period = await _context.Periods
                .Include(x => x.Task)
                .ThenInclude(x => x.User)
                .FirstOrDefaultAsync(x => x.Id == request.Id);

            if (period == null)
                return CommonResult.Fail("period do not exist");

            if (period.Task.User.Username != request.Username)
                return CommonResult.Fail("error user");

            period.IsFinish = request.IsFinish;
            period.EndTime = request.EndTime;
            period.LastModified = DateTime.Now;
            await _context.SaveChangesAsync(cancellationToken);

            return CommonResult.SUCCESS;
        }
    }
}
