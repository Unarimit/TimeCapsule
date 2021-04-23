using System;
using System.Collections.Generic;
using System.Text;
using TimeCapsule.Application.Common.Mapping;
using TimeCapsule.Domain.Entities;

namespace TimeCapsule.Application.TimeTasks.Queries.GetOftenTasks
{
    public class OftenTasksVm
    {
        public IList<TaskDto> Tasks { get; set; }

        public int Total { get; set; }
    }

    public class TaskDto : IMapFrom<TimeTask>
    {
        public TaskClassDto TaskClass { get; set; }

        public Guid Id { get; set; }

        public string Name { get; set; }

        public string Icon { get; set; }

        public bool IsFinish { get; set; }

        public bool IsCounting { get; set; }

        /*
        public void Mapping(Profile profile)
        {
            profile.CreateMap<TimeTask, TaskDto>()
                .ForMember(dest => dest.DeadLine, opt => opt.MapFrom(src => src.DeadLine == null ? null : ((DateTime)src.DeadLine).ToString("yyyy,MM,dd,HH,mm,ss")));

        }*/
    }
    public class TaskClassDto : IMapFrom<TimeTaskClass>
    {
        public Guid Id { get; set; }

        public string Name { get; set; }

        public string Color { get; set; }
    }
}
