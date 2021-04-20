using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using TimeCapsule.Application.Common.Model;
using System;

namespace TimeCapsule.Application.TimeTasks.Commands.CreateTaskAndClass
{
    /// <summary>
    /// 新建一个任务类和所属的一个任务
    /// </summary>
    public class CreateTaskAndClassCommand : IRequest<CommonResult>
    {
        public string ClassColor { get; set; }

        public string ClassName { get; set; }

        public string Username { get; set; }

        public string Name { get; set; }

        public string Desc { get; set; }

        public bool IsCounting { get; set; }

        public DateTime? DeadLine { get; set; }

        public string Icon { get; set; }

        public double AchievementPerHour { get; set; }
    }

    public class CreateTaskAndClassCommandHandler : IRequestHandler<CreateTaskAndClassCommand, CommonResult>
    {
        private readonly IApplicationDbContext _context;

        public CreateTaskAndClassCommandHandler(IApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<CommonResult> Handle(CreateTaskAndClassCommand request, CancellationToken cancellationToken)
        {
            var user = await _context.Users.FirstOrDefaultAsync(x => x.Username == request.Username);
            if (user == null)
                return CommonResult.Fail("user do not exist");

            var @class = new TimeTaskClass
            {
                User = user,
                Name = request.ClassName,
                Color = request.ClassColor
            };
            _context.TaskClasses.Add(@class);
            _context.Tasks.Add(new TimeTask
            {
                User = user,
                TaskClass = @class,
                Name = request.Name,
                Desc = request.Desc,
                Icon = request.Icon,
                IsFinish = false,
                AchievementPerHour = request.AchievementPerHour
            });

            await _context.SaveChangesAsync(cancellationToken);
            return CommonResult.SUCCESS;
        }
    }
}
