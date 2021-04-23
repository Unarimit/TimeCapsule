using TimeCapsule.Application.Common.Interfaces;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using AutoMapper;
using AutoMapper.QueryableExtensions;
using System.Linq;

namespace TimeCapsule.Application.TimeTasks.Queries.GetOftenTasks
{
    public class GetOftenTasksQuery : IRequest<OftenTasksVm>
    {
        public string Username { get; set; }
        public int Take { get; set; }

        public int Skip { get; set; }

    }

    public class GetOftenTasksQueryHandler : IRequestHandler<GetOftenTasksQuery, OftenTasksVm>
    {
        IApplicationDbContext _context;
        IMapper _mapper;

        public GetOftenTasksQueryHandler(IApplicationDbContext context, IMapper mapper)
        {
            _context = context;
            _mapper = mapper;
        }

        public async Task<OftenTasksVm> Handle(GetOftenTasksQuery request, CancellationToken cancellationToken)
        {
            var tasks = await _context.Tasks.AsNoTracking()
                .Include(x => x.User)
                .Where(x => x.User.Username == request.Username && x.IsOften == true)
                .OrderBy(x => x.Id)
                .ProjectTo<TaskDto>(_mapper.ConfigurationProvider)
                .Skip(request.Skip)
                .Take(request.Take)
                .ToListAsync(cancellationToken);

            var total = await _context.Tasks.AsNoTracking()
                .Include(x => x.User)
                .Where(x => x.User.Username == request.Username)
                .CountAsync(cancellationToken);
            return new OftenTasksVm { Tasks = tasks, Total = total};
        }
    }
}
