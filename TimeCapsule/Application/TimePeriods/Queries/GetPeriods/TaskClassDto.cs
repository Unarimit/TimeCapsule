using System;
using TimeCapsule.Application.Common.Mapping;
using TimeCapsule.Domain.Entities;

namespace TimeCapsule.Application.TimePeriods.Queries.GetPeriods
{
    public class TaskClassDto : IMapFrom<TimeTaskClass>
    {
        public string Color { get; set; }

        public string Name { get; set; }
    }
}
