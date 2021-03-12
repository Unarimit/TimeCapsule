using TimeCapsule.Domain.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace TimeCapsule.Infrastructure.Persistence.Configurations
{
    class TimeSubTaskRecordConfiguration : IEntityTypeConfiguration<TimeSubTaskRecord>
    {
        public void Configure(EntityTypeBuilder<TimeSubTaskRecord> builder)
        {
            // No Audiable Entity
        }
    }
}
