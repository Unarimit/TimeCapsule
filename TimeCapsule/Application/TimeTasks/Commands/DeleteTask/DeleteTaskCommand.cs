using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using TimeCapsule.Application.Common.Model;
using System;

namespace TimeCapsule.Application.TimeTasks.Commands.DeleteTask
{
    public class DeleteTaskCommand : IRequest<CommonResult>
    {
        public Guid Id { get; set; }

        public string Username { get; set; }
    }

    public class DeleteTaskCommandHandler : IRequestHandler<DeleteTaskCommand, CommonResult>
    {
        private readonly IApplicationDbContext _context;

        public DeleteTaskCommandHandler(IApplicationDbContext context)
        {
            _context = context;
        }
        public async Task<CommonResult> Handle(DeleteTaskCommand request, CancellationToken cancellationToken)
        {
            var task = await _context.Tasks
                .Include(x => x.User)
                .FirstOrDefaultAsync(x => x.Id == request.Id);
            if (task == null)
                return CommonResult.Fail("task do not exist");

            if (task.User.Username != request.Username)
                return CommonResult.Fail("error user");

            _context.Tasks.Remove(task);
            await _context.SaveChangesAsync(cancellationToken);

            return CommonResult.SUCCESS;
        }
    }
}
