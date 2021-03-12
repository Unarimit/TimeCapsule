using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using AutoMapper;
using AutoMapper.QueryableExtensions;

namespace TimeCapsule.Application.Users.Queries.AdminGetUserList
{
    public class AdminGetUserListQuery : IRequest<AdminGetUserListVm>
    {

    }

    public class AdminGetUserListQueryHandler : IRequestHandler<AdminGetUserListQuery, AdminGetUserListVm>
    {
        private readonly IApplicationDbContext _context;
        private readonly IMapper _mapper;

        public AdminGetUserListQueryHandler(IApplicationDbContext context, IMapper mapper)
        {
            _context = context;
            _mapper = mapper;
        }

        public async Task<AdminGetUserListVm> Handle(AdminGetUserListQuery request, CancellationToken cancellationToken)
        {
            return new AdminGetUserListVm
            {
                Users = await _context.Users
                .ProjectTo<AdminGetUserDto>(_mapper.ConfigurationProvider)
                .ToListAsync(cancellationToken),
            };
        }
    }
}
