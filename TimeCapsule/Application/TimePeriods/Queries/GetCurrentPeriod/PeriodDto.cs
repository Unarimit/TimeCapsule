using AutoMapper;
using System;
using TimeCapsule.Application.Common.Mapping;
using TimeCapsule.Domain.Entities;

namespace TimeCapsule.Application.TimePeriods.Queries.GetCurrentPeriod
{
    public class PeriodDto : PeriodVm, IMapFrom<TimePeriod>
    {
        public Guid Id { get; set; }


        // TODO: implement it later (access)
        // public TimePeriodAssess Assess { get; set; }

        public bool IsFinish { get; set; }

        public string BeginTime { get; set; }

        public string EndTime { get; set; }

        public virtual TaskDto Task { get; set; }

        public void Mapping(Profile profile)    
        {
            profile.CreateMap<TimePeriod, PeriodDto>()
                .ForMember(dest => dest.BeginTime, opt => opt.MapFrom(src => src.BeginTime.ToString("yyyy,MM,dd,HH,mm,ss")))
                .ForMember(dest => dest.EndTime, opt => opt.MapFrom(src => src.EndTime == null ? null : ((DateTime)src.EndTime).ToString("yyyy,MM,dd,HH,mm,ss")));
        }

        public class TaskDto : IMapFrom<TimeTask>
        {
            public Guid Id { get; set; }

            public string Name { get; set; }

            public string Icon { get; set; }

            public TaskClassDto TaskClass { get; set; }
        }

        public class TaskClassDto : IMapFrom<TimeTaskClass>
        {
            public string Color { get; set; }

            public string Name { get; set; }
        }
    }


}
