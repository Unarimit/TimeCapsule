using TimeCapsule.Domain.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace TimeCapsule.Infrastructure.Persistence.Configurations
{
    class TimePeriodConfiguration : IEntityTypeConfiguration<TimePeriod>
    {
        public void Configure(EntityTypeBuilder<TimePeriod> builder)
        {


        }
    }
}
