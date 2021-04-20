using Microsoft.EntityFrameworkCore;
using TimeCapsule.Domain.Entities;
using System.Threading;
using System.Threading.Tasks;


namespace TimeCapsule.Application.Common.Interfaces
{
    public interface IApplicationDbContext
    {
        DbSet<RegisterRequest> RegisterRequests { get; set; }
        DbSet<User> Users { get; set; }
        DbSet<TimeDaily> Dailies { get; set; }
        DbSet<TimePeriod> Periods { get; set; }
        DbSet<TimeTask> Tasks { get; set; }
        DbSet<TimeTaskClass> TaskClasses { get; set; }


        Task<int> SaveChangesAsync(CancellationToken cancellationToken);

    }
}
