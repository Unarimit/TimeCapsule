using System;
using TimeCapsule.Application.Common.Mapping;
using TimeCapsule.Domain.Entities;

namespace TimeCapsule.Application.TimePeriods.Queries.GetPeriods
{
    public class TaskDto : IMapFrom<TimeTask>
    {
        public Guid Id { get; set; }

        public string Name { get; set; }

        public string Icon { get; set; }

        public TaskClassDto TaskClass { get; set; }
    }
}
