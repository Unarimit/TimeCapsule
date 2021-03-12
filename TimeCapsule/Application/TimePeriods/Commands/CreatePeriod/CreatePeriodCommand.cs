using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using TimeCapsule.Application.Common.Model;
using System;
using TimeCapsule.Domain.Enums;

namespace TimeCapsule.Application.TimePeriods.Commands.CreatePeriod
{
    public class CreatePeriodCommand : IRequest<CommonResult>
    {
        public string Username { get; set; }

        public Guid TaskId { get; set; }

        public bool IsFinish { get; set; }

        public DateTime BeginTime { get; set; }

        public DateTime? EndTime { get; set; }

        public TimePeriodAssess? Assess { get; set; }
    }

    public class CreatePeriodCommandHandler : IRequestHandler<CreatePeriodCommand, CommonResult>
    {
        private readonly IApplicationDbContext _context;

        public CreatePeriodCommandHandler(IApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<CommonResult> Handle(CreatePeriodCommand request, CancellationToken cancellationToken)
        {
            var task = await _context.Tasks
                .Include(x => x.User)
                .FirstOrDefaultAsync(x => x.Id == request.TaskId);
            if (task == null)
                return CommonResult.Fail("task do not exist");
            if (task.User.Username != request.Username)
                return CommonResult.Fail("error user");


            // 将日期转为 yyyyMMdd 的数字形式
            int begin = int.Parse(request.BeginTime.ToString("yyyyMMdd"));
            var daily = await _context.Dailies.FirstOrDefaultAsync(x => x.Calendar == begin);
            if (daily == null)
            {
                daily = new TimeDaily
                {
                    Calendar = begin,
                    User = task.User,
                    Desc = ""
                };
                _context.Dailies.Add(daily);
            }

            if(request.IsFinish)
            {
                int end = int.Parse(request.EndTime?.ToString("yyyyMMdd"));
                if (begin != end) //特殊情况
                {

                }
                else
                {
                    _context.Periods.Add(new TimePeriod
                    {
                        BeginTime = request.BeginTime,
                        EndTime = request.EndTime,
                        Task = task,
                        Daily = daily,
                        IsFinish = request.IsFinish
                    });
                }

            }
            else
            {
                _context.Periods.Add(new TimePeriod
                {
                    BeginTime = request.BeginTime,
                    Task = task,
                    Daily = daily,
                    IsFinish = request.IsFinish
                });
            }


            await _context.SaveChangesAsync(cancellationToken);
            return CommonResult.SUCCESS;
        }
    }
}
