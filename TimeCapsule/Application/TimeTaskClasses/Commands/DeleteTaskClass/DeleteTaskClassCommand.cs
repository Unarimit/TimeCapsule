using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using TimeCapsule.Application.Common.Model;
using System;

namespace TimeCapsule.Application.TimeTaskClasses.Commands.DeleteTaskClass
{
    public class DeleteTaskClassCommand : IRequest<CommonResult>
    {
        public Guid Id { get; set; }

        public string Username { get; set; }
    }

    public class DeleteTaskClassCommandHandler : IRequestHandler<DeleteTaskClassCommand, CommonResult>
    {
        private readonly IApplicationDbContext _context;
        public DeleteTaskClassCommandHandler(IApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<CommonResult> Handle(DeleteTaskClassCommand request, CancellationToken cancellationToken)
        {
            var @class = await _context.TaskClasses
                .Include(x => x.User)
                .FirstOrDefaultAsync(x => x.Id == request.Id);
            if (@class == null)
                return CommonResult.Fail("taskclass do not exist");

            if (@class.User.Username != request.Username)
                return CommonResult.Fail("error user");


            _context.TaskClasses.Remove(@class);

            await _context.SaveChangesAsync(cancellationToken);

            return CommonResult.SUCCESS;
        }
    }
}
