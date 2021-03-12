using System;
using TimeCapsule.Domain.Entities;
using TimeCapsule.Application.Common.Mapping;
using AutoMapper;

namespace TimeCapsule.Application.TimeTasks.Queries.GetTasks
{
    public class TaskDto : IMapFrom<TimeTask>
    {
        public TaskClassDto TaskClass { get; set; }

        public Guid Id { get; set; }

        public string Name { get; set; }

        public string Icon { get; set; }

        public bool IsFinish { get; set; }

        public bool IsCounting { get; set; }

        public string DeadLine { get; set; }

        public void Mapping(Profile profile)
        {
            profile.CreateMap<TimeTask, TaskDto>()
                .ForMember(dest => dest.DeadLine, opt => opt.MapFrom(src => src.DeadLine == null ? null : ((DateTime)src.DeadLine).ToString("yyyy,MM,dd,HH,mm,ss")));

        }
    }
}
