using System;
using System.Collections.Generic;
using TimeCapsule.Domain.Common;

namespace TimeCapsule.Domain.Entities
{
    public class TimeTask : AudiableEntity
    {
        public Guid Id { get; set; }

        public string Name { get; set; }

        public string Desc { get; set; }

        /// <summary>
        /// 专门的接口修改该状态
        /// </summary>
        public bool IsFinish { get; set; } = false;

        public DateTime? FinishTime { get; set; }

        public string Icon { get; set; }

        public int AchievementPerHour { get; set; }


        public virtual User User { get; set; }

        /// <summary>
        /// 任务所属类别
        /// </summary>
        public virtual TimeTaskClass TaskClass { get; set; }


        public virtual IList<TimePeriod> Periods { get; set; }

    }
}
