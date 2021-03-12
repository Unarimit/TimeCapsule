using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using TimeCapsule.Application.Common.Model;
using System;

namespace TimeCapsule.Application.TimeTasks.Commands.CreateTask
{
    public class CreateTaskCommand : IRequest<CommonResult>
    {
        public Guid TaskClassId { get; set; }

        public string Name { get; set; }

        public string Desc { get; set; }

        public string Username { get; set; }

        public bool IsCounting { get; set; }

        public DateTime? DeadLine { get; set; }

        public string Icon { get; set; }

        public int AchievementPerHour { get; set; } 

        // public TimeSpan TotalTime { get; set; } default 0
    }

    public class CreateTaskCommandHandler : IRequestHandler<CreateTaskCommand, CommonResult>
    {
        private readonly IApplicationDbContext _context;
        public CreateTaskCommandHandler(IApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<CommonResult> Handle(CreateTaskCommand request, CancellationToken cancellationToken)
        {
            var user = await _context.Users.FirstOrDefaultAsync(x => x.Username == request.Username);
            if (user == null)
                return CommonResult.Fail("user do not exist");

            var @class = await _context.TaskClasses.FirstOrDefaultAsync(x => x.Id == request.TaskClassId);
            if (@class == null)
                return CommonResult.Fail("taskclass do not exist");

            _context.Tasks.Add(new TimeTask
            {
                User = user,
                TaskClass = @class,
                Name = request.Name,
                Desc = request.Desc,
                Icon = request.Icon,
                IsFinish = false,
                IsCounting = request.IsCounting,
                DeadLine = request.DeadLine,
                AchievementPerHour = request.AchievementPerHour
            });

            await _context.SaveChangesAsync(cancellationToken);

            return CommonResult.SUCCESS;
        }
    }
}
