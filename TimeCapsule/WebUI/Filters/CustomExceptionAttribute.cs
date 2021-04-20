using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Filters;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TimeCapsule.Application.Common.Exceptions;

namespace TimeCapsule.WebUI.Filters
{
    public class CustomExceptionAttribute : IExceptionFilter
    {
        public void OnException(ExceptionContext context)
        {
            if (context.Exception is CustomExceptionBase @base)
            {
                context.HttpContext.Response.StatusCode = @base.Code;
                context.Result = new ObjectResult(new ResultModel { Code = @base.Code.ToString(), Massage = context.Exception.Message });
            }
            else
            {
                context.HttpContext.Response.StatusCode = 600;
                context.Result = new ObjectResult(new ResultModel { Code = "600", Massage = context.Exception.Message });
            }
        }

        public class ResultModel
        {
            /// <summary>
            /// 错误代码, repeat
            /// </summary>
            public string Code { get; set; }

            /// <summary>
            /// 错误信息, chinglish
            /// </summary>
            public string Massage { get; set; }
        }
    }
}
