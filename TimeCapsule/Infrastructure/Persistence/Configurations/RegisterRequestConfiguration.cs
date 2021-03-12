using TimeCapsule.Domain.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace TimeCapsule.Infrastructure.Persistence.Configurations
{
    class RegisterRequestConfiguration : IEntityTypeConfiguration<RegisterRequest>
    {
        public void Configure(EntityTypeBuilder<RegisterRequest> builder)
        {
            builder.Property(o => o.Username)
                .HasMaxLength(40)
                .IsRequired();

            builder.Property(x => x.Password)
                .HasMaxLength(256)
                .IsRequired();

            builder.Property(x => x.Email)
                .HasMaxLength(128)
                .IsRequired();

            builder.Property(x => x.Desc)
                .HasMaxLength(256);



            builder.Property(x => x.LastModifiedBy)
                .HasMaxLength(40);

            builder.Property(x => x.CreatedBy)
                .HasMaxLength(40);
        }
    }
}
