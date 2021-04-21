using System;
using System.Collections.Generic;
using System.Text;
using TimeCapsule.Application.Utils;
using TimeCapsule.Domain.Entities;

namespace TimeCapsule.Application.AndroidSync.AndroidDtos
{
    public class ATimeTask
    {
        public Guid Id { get; set; }

        public Guid ClassId { get; set; }

        public string Name { get; set; }

        public string Desc { get; set; }

        public string Icon { get; set; }

        public double AchievementPerHour { get; set; }

        public bool IsOften { get; set; }

        public bool IsFinish { get; set; }

        public long BeginTime { get; set; }

        public long FinishTime { get; set; }

        /// <summary>
        /// still need attach taskclass
        /// </summary>
        /// <returns></returns>
        public TimeTask ToTimeTask(TimeTaskClass taskClass, User user)
        {
            return new TimeTask
            {
                Id = Id,
                Name = Name,
                Desc = Desc,
                Icon = Icon,
                AchievementPerHour = AchievementPerHour,
                IsOften = IsOften,
                IsFinish = IsFinish,
                BeginTime = TimeHelper.ConvertFromUnixTimestamp(BeginTime),
                FinishTime = TimeHelper.ConvertFromUnixTimestamp(FinishTime),
                TaskClass = taskClass,
                User = user
            };
        }
    }
}
