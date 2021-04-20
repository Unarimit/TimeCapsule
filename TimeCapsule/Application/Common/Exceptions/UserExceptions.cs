using System;
using System.Collections.Generic;
using System.Text;

namespace TimeCapsule.Application.Common.Exceptions
{
    public class UserExceptions
    {
        public class UnExistUserException : CustomExceptionBase
        {
            public UnExistUserException() : base(601, "user do not exist") { }
        }
    }
}
