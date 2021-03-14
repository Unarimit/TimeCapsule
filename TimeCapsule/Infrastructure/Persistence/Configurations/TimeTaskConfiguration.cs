using TimeCapsule.Domain.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace TimeCapsule.Infrastructure.Persistence.Configurations
{
    class TimeTaskConfiguration : IEntityTypeConfiguration<TimeTask>
    {
        public void Configure(EntityTypeBuilder<TimeTask> builder)
        {
            builder.Property(x => x.Name)
                .HasMaxLength(50)
                .IsRequired();

            builder.Property(x => x.Desc)
                .HasMaxLength(256);

            builder.Property(x => x.Icon)
                .HasMaxLength(512)
                .IsRequired();


        }
    }
}
