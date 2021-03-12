using System;
using TimeCapsule.Application.Common.Mapping;
using TimeCapsule.Domain.Entities;

namespace TimeCapsule.Application.TimeDailies.Queries.GetDaily
{
    public class TaskClassDto : IMapFrom<TimeTaskClass>
    {
        public Guid Id { get; set; }

        public string Name { get; set; }

        public string Color { get; set; }
    }
}
