using System;
using TimeCapsule.Domain.Entities;
using TimeCapsule.Application.Common.Mapping;

namespace TimeCapsule.Application.TimeTaskClasses.Queries.GetTaskClasses
{
    public class TaskClassDto : IMapFrom<TimeTaskClass>
    {
        public Guid Id { get; set; }

        public string Name { get; set; }

        public string Color { get; set; }
    }
}
