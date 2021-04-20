using System;
using System.Collections.Generic;
using System.Text;

namespace TimeCapsule.Application.Common.Exceptions
{
    public class TaskClassException
    {
        public class TaskClassUnexistException : CustomExceptionBase
        {
            public TaskClassUnexistException(Guid id) : base(602, $"taskClass {id} do not exist") { }
        }
    }
}
