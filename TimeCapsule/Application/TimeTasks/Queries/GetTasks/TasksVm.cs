using System;
using System.Collections.Generic;
using System.Text;

namespace TimeCapsule.Application.TimeTasks.Queries.GetTasks
{
    public class TasksVm
    {
        public IList<TaskDto> Tasks { get; set; }

        public int Total { get; set; }
    }
}
