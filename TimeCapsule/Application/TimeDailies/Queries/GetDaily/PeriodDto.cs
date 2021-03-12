using System;
using TimeCapsule.Application.Common.Mapping;
using TimeCapsule.Domain.Entities;

namespace TimeCapsule.Application.TimeDailies.Queries.GetDaily
{
    public class PeriodDto : IMapFrom<TimePeriod>
    {
        public Guid Id { get; set; }


        // TODO: implement it later (access)
        // public TimePeriodAssess Assess { get; set; }

        public bool IsFinish { get; set; }

        public DateTime BeginTime { get; set; }

        public DateTime? EndTime { get; set; }

        public virtual TaskDto Task { get; set; }
    }
}
