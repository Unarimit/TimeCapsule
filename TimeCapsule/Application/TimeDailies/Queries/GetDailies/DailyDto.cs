using System;
using System.Collections.Generic;
using System.Text;
using TimeCapsule.Application.Common.Mapping;
using TimeCapsule.Domain.Entities;

namespace TimeCapsule.Application.TimeDailies.Queries.GetDailies
{
    public class DailyDto : IMapFrom<TimeDaily>
    {
        public Guid Id { get; set; }

        public string Desc { get; set; }

        /// <summary>
        /// 按 yyyyMMdd 的方式存储日期
        /// </summary>
        public int Calendar { get; set; }
    }
}
