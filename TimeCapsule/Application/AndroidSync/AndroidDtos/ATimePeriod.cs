using System;
using System.Collections.Generic;
using System.Text;
using TimeCapsule.Application.Utils;
using TimeCapsule.Domain.Entities;

namespace TimeCapsule.Application.AndroidSync.AndroidDtos
{
    public class ATimePeriod
    {
        public Guid Id { get; set; }

        public Guid TaskId { get; set; }

        public long Begin { get; set; }

        public long End { get; set; }

        public long LastModified { get; set; }


        public TimePeriod ToTimePeriod(TimeTask task)
        {
            DateTime? end = null;
            bool isFnish = false;
            if(End != -1)
            {
                end = TimeHelper.ConvertFromUnixTimestamp(End);
                isFnish = true;
            }

            return new TimePeriod
            {
                Task = task,
                Id = Id,
                BeginTime = TimeHelper.ConvertFromUnixTimestamp(Begin),
                EndTime = end,
                LastModified = TimeHelper.ConvertFromUnixTimestamp(LastModified),
                IsFinish = isFnish
            };
        }

        public static ATimePeriod ConvertFromTimePeriod(TimePeriod period)
        {
            long end = -1;
            if(period.EndTime != null)
            {
                end = TimeHelper.ConvertToUnixTimestamp(period.EndTime ?? DateTime.Now);
            }
            return new ATimePeriod { Id = period.Id, 
                Begin = TimeHelper.ConvertToUnixTimestamp(period.BeginTime), 
                End = end, 
                LastModified = TimeHelper.ConvertToUnixTimestamp(period.LastModified), 
                TaskId = period.Task.Id };
        }
    }
}
