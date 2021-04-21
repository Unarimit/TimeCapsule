using System;
using System.Collections.Generic;
using System.Text;

namespace TimeCapsule.Application.Common.Exceptions
{
    public class TaskException
    {
        public class TaskUnexistException : CustomExceptionBase
        {
            public TaskUnexistException(Guid id) : base(603, $"task {id} do not exist") { }
        }
    }
}
