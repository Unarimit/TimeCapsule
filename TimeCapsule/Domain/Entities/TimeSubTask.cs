using System;
using System.Collections.Generic;
using System.Text;
using TimeCapsule.Domain.Common;

namespace TimeCapsule.Domain.Entities
{
    public class TimeSubTask : AudiableEntity
    {
        public Guid Id { get; set; }

        public string Name { get; set; }

        public virtual TimeTask Task { get; set; }

        public bool IsMulti { get; set; }

        public int Achievement { get; set; }

        public int Progress { get; set; }

        public int TargetProgress { get; set; }
    }
}
