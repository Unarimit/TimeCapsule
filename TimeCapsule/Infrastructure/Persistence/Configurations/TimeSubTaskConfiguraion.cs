using TimeCapsule.Domain.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace TimeCapsule.Infrastructure.Persistence.Configurations
{
    class TimeSubTaskConfiguraion : IEntityTypeConfiguration<TimeSubTask>
    {
        public void Configure(EntityTypeBuilder<TimeSubTask> builder)
        {
            builder.Property(x => x.Name)
                .HasMaxLength(50)
                .IsRequired();



        }
    }
}
