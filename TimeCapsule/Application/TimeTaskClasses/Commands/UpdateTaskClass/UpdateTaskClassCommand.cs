using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using TimeCapsule.Application.Common.Model;
using System;

namespace TimeCapsule.Application.TimeTaskClasses.Commands.UpdateTaskClass
{
    public class UpdateTaskClassCommand : IRequest<CommonResult>
    {
        public Guid Id { get; set; }

        public string Name { get; set; }

        public string Color { get; set; }

        public string Username { get; set; }
    }

    public class UpdateTaskClassCommandHandler : IRequestHandler<UpdateTaskClassCommand, CommonResult>
    {
        private readonly IApplicationDbContext _context;

        public UpdateTaskClassCommandHandler(IApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<CommonResult> Handle(UpdateTaskClassCommand request, CancellationToken cancellationToken)
        {
            var @class = await _context.TaskClasses
                .Include(x => x.User)
                .FirstOrDefaultAsync(x => x.Id == request.Id);
            if (@class == null)
                return CommonResult.Fail("taskclass do not exist");

            if (@class.User.Username != request.Username)
                return CommonResult.Fail("error user");

            @class.Name = request.Name;
            @class.Color = request.Color;

            await _context.SaveChangesAsync(cancellationToken);
            return CommonResult.SUCCESS;
        }
    }
}
