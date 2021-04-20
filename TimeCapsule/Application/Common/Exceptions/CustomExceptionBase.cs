using System;
using System.Collections.Generic;
using System.Text;

namespace TimeCapsule.Application.Common.Exceptions
{
    public class CustomExceptionBase : Exception
    {
        public string Msg { get; set; }

        public int Code { get; set; }

        public CustomExceptionBase(int code, string msg) : base(msg) { }
    }
}
