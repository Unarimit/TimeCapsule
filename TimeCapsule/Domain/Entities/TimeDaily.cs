using System;
using System.Collections.Generic;
using System.Text;
using TimeCapsule.Domain.Common;

namespace TimeCapsule.Domain.Entities
{
    public class TimeDaily : AudiableEntity
    {
        public Guid Id { get; set; }

        public virtual User User { get; set; }

        public string Desc { get; set; }

        /// <summary>
        /// 按 yyyyMMdd 的方式存储日期
        /// </summary>
        public int Calendar { get; set; }

        public virtual IList<TimePeriod> Periods { get; set; }
    }
}
