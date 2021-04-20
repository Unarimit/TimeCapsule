using System;
using System.Collections.Generic;
using System.Text;
using TimeCapsule.Domain.Common;
using TimeCapsule.Domain.Enums;

namespace TimeCapsule.Domain.Entities
{
    public class TimePeriod : AudiableEntity
    {
        public Guid Id { get; set; }

        public TimePeriodAssess Assess { get; set; }

        public bool IsFinish { get; set; }

        public DateTime BeginTime { get; set; }

        public DateTime? EndTime { get; set; }

        public virtual TimeTask Task { get; set; }

        public virtual TimeDaily Daily { get; set; }

    }
}
