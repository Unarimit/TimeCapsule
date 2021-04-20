using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using TimeCapsule.Application.Common.Model;
using System;

namespace TimeCapsule.Application.TimeTasks.Commands.UpdateTask
{
    public class UpdateTaskCommand : IRequest<CommonResult>
    {
        public Guid Id { get; set; }

        public Guid TaskClassId { get; set; }

        public string Name { get; set; }

        public string Desc { get; set; }

        public bool IsFinish { get; set; }

        public string Username { get; set; }

        public bool IsCounting { get; set; }

        public DateTime? DeadLine { get; set; }

        public string Icon { get; set; }

        public double AchievementPerHour { get; set; }
    }

    public class UpdateTaskCommandHandler : IRequestHandler<UpdateTaskCommand, CommonResult>
    {
        private readonly IApplicationDbContext _context;

        public UpdateTaskCommandHandler(IApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<CommonResult> Handle(UpdateTaskCommand request, CancellationToken cancellationToken)
        {
            var task = await _context.Tasks
                .Include(x => x.User)
                .FirstOrDefaultAsync(x => x.Id == request.Id);
            if (task == null)
                return CommonResult.Fail("task do not exist");

            if (task.User.Username != request.Username)
                return CommonResult.Fail("error user");

            var @class = await _context.TaskClasses.FirstOrDefaultAsync(x => x.Id == request.TaskClassId);
            if (@class == null)
                return CommonResult.Fail("taskclass do not exist");

            task.TaskClass = @class;
            task.Name = request.Name;
            task.Desc = request.Desc;
            task.Icon = request.Icon;
            task.IsFinish = request.IsFinish;
            task.AchievementPerHour = request.AchievementPerHour;

            await _context.SaveChangesAsync(cancellationToken);

            return CommonResult.SUCCESS;
        }
    }
}
