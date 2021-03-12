using System;
using System.Collections.Generic;
using System.Text;

namespace TimeCapsule.Domain.Entities
{
    public class TimeSubTaskRecord
    {
        public Guid Id { get; set; }

        public int IncreProgress { get; set; }

        public virtual TimePeriod Period { get; set; }
    }
}
