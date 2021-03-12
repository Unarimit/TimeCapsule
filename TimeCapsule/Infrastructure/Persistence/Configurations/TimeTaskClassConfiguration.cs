using TimeCapsule.Domain.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace TimeCapsule.Infrastructure.Persistence.Configurations
{
    class TimeTaskClassConfiguration : IEntityTypeConfiguration<TimeTaskClass>
    {
        public void Configure(EntityTypeBuilder<TimeTaskClass> builder)
        {
            builder.Property(x => x.Name)
                .HasMaxLength(40)
                .IsRequired();

            builder.Property(x => x.Color)
                .HasMaxLength(10)
                .IsRequired();


            builder.Property(x => x.LastModifiedBy)
                .HasMaxLength(40);

            builder.Property(x => x.CreatedBy)
                .HasMaxLength(40)
                .IsRequired();
        }
    }
}
