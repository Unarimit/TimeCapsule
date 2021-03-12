using System;
using System.Reflection;
using System.Threading;
using System.Threading.Tasks;
using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Common;
using TimeCapsule.Domain.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;

namespace TimeCapsule.Infrastructure.Persistence
{
    public class ApplicationDbContext : DbContext, IApplicationDbContext
    {

        private readonly IDateTimeService _dateTime; 
        private readonly ICurrentUserService _currentUserService;
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options,
            ICurrentUserService currentUserService,
            IDateTimeService dateTime) : base(options)
        {
            _currentUserService = currentUserService;
            _dateTime = dateTime;
        }
        public DbSet<RegisterRequest> RegisterRequests { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<TimeDaily> Dailies { get; set; }
        public DbSet<TimePeriod> Periods { get; set; }
        public DbSet<TimeSubTask> SubTasks { get; set; }
        public DbSet<TimeSubTaskRecord> SubTaskRecords { get; set; }
        public DbSet<TimeTask> Tasks { get; set; }
        public DbSet<TimeTaskClass> TaskClasses { get; set; }

        public override Task<int> SaveChangesAsync(CancellationToken cancellationToken = new CancellationToken())
        {
            foreach (var entry in ChangeTracker.Entries<AudiableEntity>())
            {
                switch (entry.State)
                {
                    // TODO: when not debug, delete ??"debug" flollowing
                    case EntityState.Added:
                        entry.Entity.CreatedBy = _currentUserService.Username ?? "debug";
                        entry.Entity.Created = DateTime.Now;
                        break;
                    case EntityState.Modified:
                        entry.Entity.LastModifiedBy = _currentUserService.Username;
                        entry.Entity.LastModified = DateTime.Now;
                        break;
                }
            }
            return base.SaveChangesAsync(cancellationToken);
        }


        protected override void OnModelCreating(ModelBuilder builder)
        {
            builder.Entity<TimePeriod>().HasOne(x => x.Daily).WithMany(x => x.Periods).IsRequired();
            builder.Entity<TimePeriod>().HasOne(x => x.Task).WithMany(x => x.Periods).IsRequired();
            builder.Entity<TimeTask>().HasOne(x => x.User).WithMany(x => x.Tasks).IsRequired();
            builder.Entity<TimeTask>().HasOne(x => x.TaskClass).WithMany(x => x.Tasks).IsRequired();
            builder.Entity<TimeTaskClass>().HasOne(x => x.User).WithMany(x => x.TaskClasses).IsRequired();
            builder.Entity<TimeDaily>().HasOne(x => x.User).WithMany(x => x.Dailies).IsRequired();
            builder.Entity<TimeSubTask>().HasOne(x => x.Task).WithMany(x => x.SubTasks).IsRequired();
            builder.Entity<TimeSubTaskRecord>().HasOne(x => x.Period).WithMany(x => x.SubTaskRecords).IsRequired();
            builder.ApplyConfigurationsFromAssembly(Assembly.GetExecutingAssembly());

            base.OnModelCreating(builder);
        }
    }
}
