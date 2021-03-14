using TimeCapsule.Domain.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace TimeCapsule.Infrastructure.Persistence.Configurations
{
    class TimeDailyConfiguration : IEntityTypeConfiguration<TimeDaily>
    {
        public void Configure(EntityTypeBuilder<TimeDaily> builder)
        {
            builder.Property(x => x.Desc)
                .HasMaxLength(2048);

            

        }
    }
}
