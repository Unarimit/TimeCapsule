using TimeCapsule.Application.Common.Interfaces;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using AutoMapper;
using AutoMapper.QueryableExtensions;
using System.Linq;

namespace TimeCapsule.Application.TimeTaskClasses.Queries.GetTaskClasses
{
    public class GetTaskClassesQuery : IRequest<TaskClassesVm>
    {
        public string Username;
    }

    public class GetTaskClassesQueryHandler : IRequestHandler<GetTaskClassesQuery, TaskClassesVm>
    {
        private readonly IApplicationDbContext _context;
        private readonly IMapper _mapper;

        public GetTaskClassesQueryHandler(IApplicationDbContext context, IMapper mapper)
        {
            _context = context;
            _mapper = mapper;
        }

        public async Task<TaskClassesVm> Handle(GetTaskClassesQuery request, CancellationToken cancellationToken)
        {
            var list = await _context.TaskClasses.AsNoTracking()
                .Include(x => x.User)
                .Where(x => x.User.Username == request.Username)
                .ProjectTo<TaskClassDto>(_mapper.ConfigurationProvider)
                .ToListAsync(cancellationToken);

            return new TaskClassesVm { Classes = list };
        }
    }
}
