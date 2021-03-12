using System;
using System.Collections.Generic;
using System.Text;

namespace TimeCapsule.Application.Common.Model
{
    public class CommonResult
    {
        public bool Succeed { get; set; }

        public string Message { get; set; }

        public static CommonResult SUCCESS { get; } = new CommonResult
        {
            Succeed = true,
            Message = "ok"
        };

        /*
        public static CommonResult Success()
        {
            return new CommonResult
            {
                Succeed = true,
                Message = string.Empty
            };
        }*/

        public static CommonResult Fail(string msg)
        {
            return new CommonResult
            {
                Succeed = false,
                Message = msg
            };
        }
    }
}
