using TimeCapsule.Application.Common.Interfaces;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using TimeCapsule.Application.Common.Model;
using System;

namespace TimeCapsule.Application.TimeTasks.Commands.FinishTask
{
    public class FinishTaskCommand : IRequest<CommonResult>
    {
        public Guid Id { get; set; }

        public bool IsFinish { get; set; }

        public DateTime FinishTime { get; set; }

        public string Username { get; set; }
    }

    public class FinishTaskCommandHandler : IRequestHandler<FinishTaskCommand, CommonResult>
    {
        IApplicationDbContext _context;
        public FinishTaskCommandHandler(IApplicationDbContext context)
        {
            _context = context;
        }
        public async Task<CommonResult> Handle(FinishTaskCommand request, CancellationToken cancellationToken)
        {
            var task = await _context.Tasks
                .Include(x => x.User)
                .FirstOrDefaultAsync(x => x.Id == request.Id);
            if (task == null)
                return CommonResult.Fail("task do not exist");

            if (task.User.Username != request.Username)
                return CommonResult.Fail("error user");

            task.IsFinish = request.IsFinish;
            task.FinishTime = request.FinishTime;

            await _context.SaveChangesAsync(cancellationToken);
            return CommonResult.SUCCESS;
        }
    }
}
