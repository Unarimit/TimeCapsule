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

        public string Icon { get; set; }

        public double AchievementPerHour { get; set; }

        /// <summary>
        /// 专门的接口修改该状态
        /// </summary>
        public bool IsFinish { get; set; } = false;

        public bool IsOften { get; set; } = false; // TODO: 完成该属性的显示功能

        public DateTime BeginTime { get; set; } = DateTime.Now;

        public DateTime? FinishTime { get; set; }

        public virtual User User { get; set; }

        /// <summary>
        /// 任务所属类别
        /// </summary>
        public virtual TimeTaskClass TaskClass { get; set; }

        public virtual IList<TimePeriod> Periods { get; set; }

    }
}
