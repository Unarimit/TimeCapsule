using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;

namespace TimeCapsule.Application.RegisterRequests.Commands
{
    public class CreateRegisterRequestCommand : IRequest
    {
        public string Username { get; set; }

        public string Email { get; set; }

        public string Password { get; set; }

        public string Desc { get; set; }
    }

    public class CreateRegisterRequestCommandHandler : IRequestHandler<CreateRegisterRequestCommand>
    {
        private readonly IApplicationDbContext _context;
        public CreateRegisterRequestCommandHandler(IApplicationDbContext context)
        {
            _context = context;
        }
        public async Task<Unit> Handle(CreateRegisterRequestCommand request, CancellationToken cancellationToken)
        {
            //TODO: 明文密码不行
            //TODO: 传入参数的验证也没有加

            // 用户名重复无法提交申请
            var db_entity = await _context.RegisterRequests.AsNoTracking()
                .FirstOrDefaultAsync(x => x.Username == request.Username);
            if(db_entity != null)
                return Unit.Value;

            RegisterRequest entity = new RegisterRequest
            {
                Username = request.Username,
                Password = request.Password,
                Email = request.Email,
                Desc = request.Desc
            };

            _context.RegisterRequests.Add(entity);
            await _context.SaveChangesAsync(cancellationToken);
            return Unit.Value;
        }
    }
}
