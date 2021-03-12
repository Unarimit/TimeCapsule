using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace TimeCapsule.Application.Users.Queries.LoginUser
{
    public class LoginUserQuery : IRequest<LoginUserBack>
    {
        /// <summary>
        /// 用户名或密码
        /// </summary>
        public string UsernameOrEmail { get; set; }

        /// <summary>
        /// 密码
        /// </summary>
        public string Password { get; set; }
    }

    public class LoginUserQueryHandler : IRequestHandler<LoginUserQuery, LoginUserBack>
    {
        private readonly IApplicationDbContext _context;

        public LoginUserQueryHandler(IApplicationDbContext context)
        {
            _context = context;
        }
        public async Task<LoginUserBack> Handle(LoginUserQuery request, CancellationToken cancellationToken)
        {
            User user = null;
            if (request.UsernameOrEmail.Contains("@"))
            {
                user = await _context.Users.AsNoTracking()
                    .FirstOrDefaultAsync(x => x.Email == request.UsernameOrEmail);
            }
            else
            {
                user = await _context.Users.AsNoTracking()
                    .FirstOrDefaultAsync(x => x.Username == request.UsernameOrEmail);
            }

            

            if (user == null)
            {
                return null;
            }
            else if (user.Password != request.Password)
            {
                // TODO: 明文验证禁止，预计在数据库中建一个配置表存seed
                return null;
            }
            else
            {
                return new LoginUserBack
                {
                    IsAdmin = user.IsAdmin,
                    IsUser = user.IsUser,
                    Username = user.Username
                };
            }
        }
    }
}
