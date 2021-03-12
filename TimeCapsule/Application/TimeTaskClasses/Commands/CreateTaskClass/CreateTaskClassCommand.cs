using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using TimeCapsule.Application.Common.Model;

namespace TimeCapsule.Application.TimeTaskClasses.Commands.CreateTaskClass
{
    public class CreateTaskClassCommand : IRequest<CommonResult>
    {
        public string Name { get; set; }

        public string Color { get; set; }

        public string Username { get; set; }
    }

    public class CreateTaskClassCommandHandler : IRequestHandler<CreateTaskClassCommand, CommonResult>
    {
        private readonly IApplicationDbContext _context;

        public CreateTaskClassCommandHandler(IApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<CommonResult> Handle(CreateTaskClassCommand request, CancellationToken cancellationToken)
        {
            var user = await _context.Users.FirstOrDefaultAsync(x => x.Username == request.Username);
            if (user == null)
                return CommonResult.Fail("user do not exist");

            _context.TaskClasses.Add(new TimeTaskClass
            {
                Name = request.Name,
                Color = request.Color,
                User = user
            });

            await _context.SaveChangesAsync(cancellationToken);
            return CommonResult.SUCCESS;
        }
    }
}
