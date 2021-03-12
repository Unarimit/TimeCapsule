using System;
using System.Collections.Generic;
using System.Text;
using TimeCapsule.Domain.Common;

namespace TimeCapsule.Domain.Entities
{
    public class TimeTaskClass : AudiableEntity
    {
        public Guid Id { get; set; }

        public string Name { get; set; }

        public string Color { get; set; }

        public virtual User User { get; set; }

        public virtual IList<TimeTask> Tasks { get; set; }
    }
}
